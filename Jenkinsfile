pipeline {
    agent any
    stages {
        stage('CLEAN') {
            steps {
				sh 'mvn clean'
            }
        }
        stage('BUILD') {
            steps {
                sh 'mvn package -DskipTests=true'				
            }
        }
        stage('DOCKER-BUILD') {
            steps {
                sh 'docker build -t task-manager/primefaces .'
            }
        }
        stage('DOCKER-RUN') {
     	    steps {
        	    sh 'docker stop tmp || true'
            	sh 'docker rm tmp || true'
            	sh 'docker run -d --name="tmp" --hostname="tmp" --network="volnenkoschool_default" task-manager/primefaces'
            }
        }
    }
	post {
	    always {
	        archiveArtifacts artifacts: '**/*.war',
		    onlyIfSuccessful: true
	    }
	}	
}