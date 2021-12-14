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
                    git([url: 'git@github.com:milvus-io/milvus.git', branch: 'master'])
                    sh "echo ${image_tag}"
                    sh 'ls -lah'
                }
            }
        }
  }
}