#!/usr/bin/env groovy

pipeline {
    agent any
    stages{
        stage ('Deploy Test') {
            agent {
                kubernetes {
                    label 'deploy-test'
                    defaultContainer 'main'
                    yamlFile 'pod/deploy-test.yaml'
                    customWorkspace '/home/jenkins/agent/workspace'
                }
            }
            steps {
                container('main') {
                    sh 'git clone https://github.com/milvus-io/milvus.git'
                    sh "echo ${image_tag}"
                    sh 'ls -lah'
                }
            }
        }
  }
}