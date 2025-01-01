pipeline {
    agent any
    tools {
        maven 'maven'
        jdk 'Java'
        dockerTool 'Docker' // Use the configured docker tool
    }
    stages {
        stage('Checkout') {
            steps {
                 checkout scmGit(
                    branches: [[name: '*/main']],
                    extensions: [],
                    userRemoteConfigs: [[credentialsId: 'ser3elah', url: 'https://github.com/projet-fintech/Authentification-Service.git']]
                )
             }
        }
         stage('Prepare Dependencies') {
            steps {
                script {
                    // Copier events-lib depuis le dossier shared-artifacts
                    sh '''
                        mkdir -p repo/com/banque/events-lib/1.0-SNAPSHOT
                        cp /var/jenkins_home/shared-artifacts/repo/com/banque/events-lib/1.0-SNAPSHOT/events-lib-1.0-SNAPSHOT.jar ./events-lib-1.0-SNAPSHOT.jar
                    '''
                }
            }
        }
         stage('Build Docker Image') {
            steps {
                script {
                    def imageName = "authentification-microservice:${BUILD_NUMBER}"
                    sh "docker build -t ${imageName} ."
                }
            }
        }
       }
    }
