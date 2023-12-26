def call() {
    pipeline {
        agent {
            label 'workstation'
        }

        stages {
            stage('compile/build') {
                steps {
                    script {
                        common.compile()
                    }
                }
            }

            stage('Unit Test') {
                steps {
                    echo 'unit test'
                }
            }

            stage('Quality control') {
                steps {
                    echo "Quality control"
                }
            }

            stage('Upload Code') {
                steps {
                    echo "code upload"
                }
            }
        }
    }
}