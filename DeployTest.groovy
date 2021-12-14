#!/usr/bin/env groovy

pipeline {
    agent {
            kubernetes {
                label 'deploy-test'
                defaultContainer 'main'
                yamlFile 'pod/deploy-test.yaml'
                customWorkspace '/home/jenkins/agent/workspace'
            }
    }
    stages{
        stage ('Deploy Test') {

            steps {
                container('main') {
                    // sh 'git clone https://github.com/milvus-io/milvus.git'
                    sh "echo ${image_tag}"
                    sh 'ls -lah'
                }
            }
        }
   }
}