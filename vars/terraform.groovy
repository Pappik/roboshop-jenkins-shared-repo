def call() {
    pipeline {

        options {
            ansiColor('xterm')
        }

        agent {
            node {
                label 'workstation'
            }
        }

        parameters {
            string(name: 'INFRA_ENV', defaultValue: '', description: 'Enter env like dev or prod')
            choice(name: 'ACTION', choices: ['apply', 'destroy'], description: 'Choose apply or destroy')
        }

        stages {

            stage('Terraform init') {
                steps {
                    sh 'terraform init -backend-config=env-${INFRA_ENV}/state.tfvars'

                }
            }


            stage('Terraform ${ACTION}') {
                steps {
                    sh 'terraform ${ACTION} -auto-approve -var-file=env-${INFRA_ENV}/main.tfvars'

                }
            }



        }
        post {
            always {
                cleanWs()
            }
        }

    }

}