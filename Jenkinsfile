pipeline {
    agent any

    tools {
        jdk   'JDK'
        maven 'MAVEN'
    }

    // --- Trigger configuration ---
    triggers {
        // Scheduled: every day at midnight
        cron('H 0 * * *')
        // Git push: via webhook (configured in Step 6)
        githubPush()
    }

    // --- Switchable parameters ---
    parameters {
        choice(
            name: 'EXECUTION_ENV',
            choices: ['local', 'sauce'],
            description: 'Run tests locally or on SauceLabs'
        )
        choice(
            name: 'BROWSER',
            choices: ['chrome', 'firefox'],
            description: 'Browser to run tests on'
        )
        string(
            name: 'THREAD_COUNT',
            defaultValue: '2',
            description: 'Parallel thread count'
        )
    }

    environment {
        ALLURE_RESULTS = 'target/allure-results'
        JAVA_HOME = "C:\\Users\\arpan\\.jdks\\ms-21.0.10"
        PATH      = "${JAVA_HOME}\\bin;${env.PATH}"
    }

    stages {

        stage('Debug ENV') {
            steps {
                bat 'echo JAVA_HOME=%JAVA_HOME%'
                bat 'java -version'
            }
        }

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/arpankush/TestNGFramework.git'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean compile -q'
            }
        }

        stage('Test — Local') {
            when {
                expression { params.EXECUTION_ENV == 'local' }
            }
            steps {
                bat """
                    mvn test ^
                    -Dbrowser=${params.BROWSER} ^
                    -Dthread.count=${params.THREAD_COUNT} ^
                    --no-transfer-progress
                """
            }
        }

        stage('Test — SauceLabs') {
            when {
                expression { params.EXECUTION_ENV == 'sauce' }
            }
            environment {
                SAUCE           = 'true'
                SAUCE_USERNAME  = credentials('SAUCE_USERNAME')
                SAUCE_ACCESS_KEY = credentials('SAUCE_ACCESS_KEY')
            }
            steps {
                bat """
                    mvn test ^
                    -Dbrowser=${params.BROWSER} ^
                    -Dthread.count=${params.THREAD_COUNT} ^
                    --no-transfer-progress
                """
            }
        }
    }

    post {
        always {
            allure([
                includeProperties : false,
                jdk               : '',
                commandline       : 'AllureCLI',
                results           : [[path: 'target/allure-results']]
            ])
        }
    }
}