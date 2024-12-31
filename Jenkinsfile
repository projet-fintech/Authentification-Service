pipeline {
    agent any
    tools {
        maven 'maven'
        jdk 'Java'
         docker 'Docker' // Use the configured docker tool
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
        stage('Build Docker Image') {
            steps {
                withDockerContainer(toolName: 'docker',
                        image:'openjdk:21-jdk-slim'
                    ) {
                   withEnv(["DOCKER_HOST" : "unix:///var/run/docker.sock","DOCKER_INSTALLED":"true"]) {
                    sh "docker build -t my-${JOB_NAME}:${BUILD_NUMBER} ."
                     }
                   }
              }
         }
       }
    }
