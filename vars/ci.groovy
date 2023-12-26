def call() {
    pipeline {
        agent {
            label 'workstation'
        }

        stages {
            stage('compile/build') {
                steps {
                    echo "compile"
                }
            }

            stage('Unit Test') {
                steps {
                    script {
                        common.compile()
                    }
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