def call() {
    pipeline {
        agent any

        string(name: 'APP_ENV', defaultValue: '', description: 'Enter env like dev or prod')
        string(name: 'COMPONENT',defaultValue: '', description: 'Enter component name')
        string(name: 'APP_VERSION',defaultValue: '', description: 'Enter app version to deploy')


        stages {
            stage('Run Deployment') {
                steps {
                    sh '''
                      aws ssm put-parameter --name "
                      ${APP_ENV}.${COMPONENT}.APP_VERSION"  --type "String" -value "${APP_VERSION}" --overwrite

                   '''
                }
            }
        }
    }
}