// Получаем ветку, если параметр TEST_BRANCH_NAME не задан — используем main
def task_branch = env.TEST_BRANCH_NAME ?: "main"
def branch_cutted = task_branch.contains("origin") ? task_branch.split('/')[1] : task_branch.trim()
currentBuild.displayName = branch_cutted
def base_git_url = "https://github.com/wallensteinMorgan/KotlinAT.git"

pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                cleanWs()
                checkout scm
                script {
                    echo "=== WORKSPACE VERIFICATION ==="
                    if (isUnix()) {
                        sh """
                            pwd
                            ls -la
                        """
                    } else {
                        bat "dir"
                    }
                }
            }
        }

        stage('Clean Project') {
            steps {
                script {
                    if (isUnix()) {
                        sh './gradlew clean --no-daemon'
                    } else {
                        bat 'gradlew.bat clean'
                    }
                }
            }
        }

        stage('Run Tests in Parallel') {
            parallel {
                stage('API Tests') {
                    steps {
                        script { runTestWithTag("apiTests") }
                    }
                }
                stage('UI Tests') {
                    steps {
                        script { runTestWithTag("uiTests") }
                    }
                }
            }
        }

        stage('Allure Report') {
            steps {
                script {
                    // Создаём папку на случай если тесты не создали
                    if (isUnix()) {
                        sh 'mkdir -p build/allure-results || true'
                    } else {
                        bat 'mkdir build\\allure-results || exit 0'
                    }

                    // Генерируем Allure
                    allure([
                            includeProperties: true,
                            jdk: '',
                            reportBuildPolicy: 'ALWAYS',
                            results: [[path: 'build/allure-results']]
                    ])
                }
            }
        }
    }

    post {
        always {
            echo "=== COLLECTING TEST RESULTS ==="
            // Сбор JUnit тестов для Jenkins
            script {
                if (isUnix()) {
                    junit 'build/test-results/**/*.xml'
                } else {
                    junit 'build\\test-results\\**\\*.xml'
                }
            }

            // Лог финального результата
            script {
                if (currentBuild.result == 'UNSTABLE') {
                    echo "Some tests failed but build continued"
                } else if (currentBuild.result == 'FAILURE') {
                    echo "Build failed"
                } else {
                    echo "Build successful"
                }
            }
        }
    }
}

// Вспомогательная функция для запуска тестов
def runTestWithTag(String tag) {
    try {
        echo "Running ${tag} tests"

        if (isUnix()) {
            sh """
                ./gradlew ${tag} \
                -Dallure.results.directory=build/allure-results \
                --no-daemon \
                --stacktrace \
                --info
            """
        } else {
            bat """
                gradlew.bat ${tag} ^
                -Dallure.results.directory=build\\allure-results ^
                --no-daemon ^
                --stacktrace ^
                --info
            """
        }

    } catch (err) {
        echo "Some tests failed in ${tag}: ${err}"
        currentBuild.result = 'UNSTABLE'

        // Создаём папку на случай если тесты не создали
        if (isUnix()) {
            sh 'mkdir -p build/allure-results || true'
        } else {
            bat 'mkdir build\\allure-results || exit 0'
        }
    } finally {
        echo "Finished tests for ${tag}"
    }
}