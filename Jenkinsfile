pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven 'Maven3'
    }

    stages {
        stage('Checkout') {
            steps {
                // Get some code from a GitHub repository
                //git 'https://github.com/vunicjovan/qa-learning.git'
                git branch: 'main',
                    url: 'https://github.com/vunicjovan/qa-learning.git'
            }
        }
        stage('Maven build') {
            steps {
                // To run Maven on a Windows agent, use
                bat 'mvn clean install > log-file.log'
            }
        }
    }
}
