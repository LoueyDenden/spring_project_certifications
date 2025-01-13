pipeline {
    agent any
    tools {
        maven 'maven-399'
    }
    triggers {
        pollSCM('* * * * *')
    }

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub')
        IMAGE_NAME_CONFIG_SERVER = 'loueydenden/config-server'
        IMAGE_NAME_DISCOVERY_SERVICE = 'loueydenden/discovery-service'
        IMAGE_NAME_GATEWAY = 'loueydenden/gateway'
        IMAGE_NAME_USER_SERVICE = 'loueydenden/user-service'
        IMAGE_NAME_CERTIFICATIONS_SERVICE = 'loueydenden/certifications-service'
        IMAGE_NAME_ORDER_SERVICE = 'loueydenden/order-service'
        IMAGE_NAME_LIBRARY_SERVICE = 'loueydenden/library-service'
        IMAGE_NAME_FRONTEND = 'loueydenden/client'
        BUILD_ID = "${env.BUILD_ID}"
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    // Perform the Git checkout
                    checkout([
                        $class: 'GitSCM',
                        branches: [[name: 'main']],
                        userRemoteConfigs: [[
                            url: 'git@github.com:LoueyDenden/spring_project_certifications.git',
                            credentialsId: 'github_key'
                        ]]
                    ])
                    // Get the list of all changed files
                    def changes = sh(
                        script: "git diff --name-only HEAD~1..HEAD || true",
                        returnStdout: true
                    ).trim().split("\n")
                    echo "Changed files: ${changes}"
                    // Check if all changes are inside project_charts
                    def onlyInProjectCharts = changes.every { it.startsWith('project_charts/') || it.startsWith('kubernetes/') || it.startsWith('screens/') || it.startsWith('README.md') }
                    if (onlyInProjectCharts) {
                        echo "Changes are exclusively in project_charts or kuberenetes or screens. Skipping the pipeline."
                        currentBuild.description = "Skipped: Changes only in project_charts or kuberenetes or screens"
                        env.SKIP_PIPELINE = true
                        return // Exit the pipeline
                    }
                    echo "Changes are not limited to project_charts or kuberenetes or screens. Proceeding with the pipeline."
                }
            }
        }
        stage('Build Config Server Image') {
            //when { changeset "config-server/**"}
            steps {
                dir('config-server') {
                    script {
                        dockerImageConfigServer = docker.build("${IMAGE_NAME_CONFIG_SERVER}:${BUILD_ID}")
                    }
                }
            }
        }
        stage('Build Discovery Service Image') {
            //when { changeset "discovery-service/**"}
            steps {
                dir('discovery-service') {
                    script {
                        dockerImageDiscoveryService = docker.build("${IMAGE_NAME_DISCOVERY_SERVICE}:${BUILD_ID}")
                    }
                }
            }
        }
        stage('Build Gateway Image') {
            //when { changeset "gateway/**"}
            steps {
                dir('gateway') {
                    script {
                        dockerImageGateway = docker.build("${IMAGE_NAME_GATEWAY}:${BUILD_ID}")
                    }
                }
            }
        }
        stage('Build User Image') {
            //when { changeset "user-service/**"}
            steps {
                dir('user-service') {
                    script {
                        dockerImageUserService = docker.build("${IMAGE_NAME_USER_SERVICE}:${BUILD_ID}")
                    }
                }
            }
        }
        stage('Build Certifications Image') {
            //when { changeset "certifications-service/**"}
            steps {
                dir('certifications-service') {
                    script {
                        dockerImageCertificationsService = docker.build("${IMAGE_NAME_CERTIFICATIONS_SERVICE}:${BUILD_ID}")
                    }
                }
            }
        }
        stage('Build Order Image') {
            //when { changeset "order-service/**"}
            steps {
                dir('order-service') {
                    script {
                        dockerImageOrderService = docker.build("${IMAGE_NAME_ORDER_SERVICE}:${BUILD_ID}")
                    }
                }
            }
        }
        stage('Build Library Image') {
            //when { changeset "library-service/**"}
            steps {
                dir('library-service') {
                    script {
                        dockerImageLibraryService = docker.build("${IMAGE_NAME_LIBRARY_SERVICE}:${BUILD_ID}")
                    }
                }
            }
        }
        stage('Build FrontEnd Image') {
            //when { changeset "UI_Spring/**"}
            steps {
                dir('UI_Spring') {
                    script {
                        dockerImageFront = docker.build("${IMAGE_NAME_FRONTEND}:${BUILD_ID}")
                    }
                }
            }
        }

        //scan trivy
        stage('Scan Config Server Image') {
            //when { changeset "config-server/**"}
            steps {
                script {
                    sh """
                    docker run --rm -v /var/run/docker.sock:/var/run/docker.sock \\
                    -e TRIVY_DB_REPO=ghcr.io/aquasecurity/trivy-db \\
                    aquasec/trivy:latest image --exit-code 0 --scanners vuln --no-progress --timeout 20m --severity LOW,MEDIUM,HIGH,CRITICAL \\
                    ${IMAGE_NAME_CONFIG_SERVER}:${BUILD_ID}
                    """
                }
            }
        }

        stage('Scan Discovery Service Image') {
            //when { changeset "discovery-service/**"}
            steps {
                script {
                    sh """
                    docker run --rm -v /var/run/docker.sock:/var/run/docker.sock \\
                    -e TRIVY_DB_REPO=ghcr.io/aquasecurity/trivy-db \\
                    aquasec/trivy:latest image --exit-code 0 --scanners vuln --no-progress --timeout 20m --severity LOW,MEDIUM,HIGH,CRITICAL \\
                    ${IMAGE_NAME_DISCOVERY_SERVICE}:${BUILD_ID}
                    """
                }
            }
        }

        stage('Scan Gateway Image') {
            //when { changeset "gateway/**"}
            steps {
                script {
                    sh """
                    docker run --rm -v /var/run/docker.sock:/var/run/docker.sock \\
                    -e TRIVY_DB_REPO=ghcr.io/aquasecurity/trivy-db \\
                    aquasec/trivy:latest image --exit-code 0 --scanners vuln --no-progress --timeout 20m --severity LOW,MEDIUM,HIGH,CRITICAL \\
                    ${IMAGE_NAME_GATEWAY}:${BUILD_ID}
                    """
                }
            }
        }

        stage('Scan Certifications Service Image') {
            //when { changeset "certifications-service/**"}
            steps {
                script {
                    sh """
                    docker run --rm -v /var/run/docker.sock:/var/run/docker.sock \\
                    -e TRIVY_DB_REPO=ghcr.io/aquasecurity/trivy-db \\
                    aquasec/trivy:latest image --exit-code 0 --scanners vuln --no-progress --timeout 20m --severity LOW,MEDIUM,HIGH,CRITICAL \\
                    ${IMAGE_NAME_CERTIFICATIONS_SERVICE}:${BUILD_ID}
                    """
                }
            }
        }

        stage('Scan User Service Image') {
            //when { changeset "user-service/**"}
            steps {
                script {
                    sh """
                    docker run --rm -v /var/run/docker.sock:/var/run/docker.sock \\
                    -e TRIVY_DB_REPO=ghcr.io/aquasecurity/trivy-db \\
                    aquasec/trivy:latest image --exit-code 0 --scanners vuln --no-progress --timeout 20m --severity LOW,MEDIUM,HIGH,CRITICAL \\
                    ${IMAGE_NAME_USER_SERVICE}:${BUILD_ID}
                    """
                }
            }
        }

        stage('Scan Library Service Image') {
            //when { changeset "library-service/**"}
            steps {
                script {
                    sh """
                    docker run --rm -v /var/run/docker.sock:/var/run/docker.sock \\
                    -e TRIVY_DB_REPO=ghcr.io/aquasecurity/trivy-db \\
                    aquasec/trivy:latest image --exit-code 0 --scanners vuln --no-progress --timeout 20m --severity LOW,MEDIUM,HIGH,CRITICAL \\
                    ${IMAGE_NAME_LIBRARY_SERVICE}:${BUILD_ID}
                    """
                }
            }
        }

        stage('Scan Order Service Image') {
            //when { changeset "order-service/**"}
            steps {
                script {
                    sh """
                    docker run --rm -v /var/run/docker.sock:/var/run/docker.sock \\
                    -e TRIVY_DB_REPO=ghcr.io/aquasecurity/trivy-db \\
                    aquasec/trivy:latest image --exit-code 0 --scanners vuln --no-progress --timeout 20m --severity LOW,MEDIUM,HIGH,CRITICAL \\
                    ${IMAGE_NAME_ORDER_SERVICE}:${BUILD_ID}
                    """
                }
            }
        }

        stage('Scan Client Service Image') {
            //when { changeset "UI_Spring/**"}
            steps {
                script {
                    sh """
                    docker run --rm -v /var/run/docker.sock:/var/run/docker.sock \\
                    -e TRIVY_DB_REPO=ghcr.io/aquasecurity/trivy-db \\
                    aquasec/trivy:latest image --exit-code 0 --scanners vuln --no-progress --timeout 20m --severity LOW,MEDIUM,HIGH,CRITICAL \\
                    ${IMAGE_NAME_FRONTEND}:${BUILD_ID}
                    """
                }
            }
        }

        stage('Push Config Server Image to Docker Hub') {
            //when { changeset "config-server/**"}
                steps {
                    script {
                            docker.withRegistry('https://index.docker.io/v1/', 'dockerhub') {
                                    dockerImageConfigServer.push()
                                }
                            sh 'echo "UPDATING TAG IN HELM CHARTS"'
                            withCredentials([sshUserPrivateKey(credentialsId: 'github_key', keyFileVariable: 'SSH_KEY')]) {
                                sh """
                                    git clone git@github.com:LoueyDenden/spring_project_certifications.git temp_repo
                                    cd temp_repo/project_charts/charts/configserver
                                    sed -i '/^  tag: / s/: .*/: "${BUILD_ID}"/' values.yaml
                                    git config user.name "Louey Denden"
                                    git config user.email "louey.denden@polytechnicien.tn"
                                    git add values.yaml
                                    git commit -m "CustomUpdated tag to ${BUILD_ID} in helm chart"
                                    git push origin main
                                    cd ../../../..
                                    rm -R temp_repo
                                    """
                                    }
                            }
                        }
            }
        stage('Push Discovery Service Image to Docker Hub') {
            //when { changeset "discovery-service/**"}
                steps {
                    script {
                                docker.withRegistry('https://index.docker.io/v1/', 'dockerhub') {
                                        dockerImageDiscoveryService.push()
                                }
                            sh 'echo "UPDATING TAG IN HELM CHARTS"'
                            withCredentials([sshUserPrivateKey(credentialsId: 'github_key', keyFileVariable: 'SSH_KEY')]) {
                                sh """
                                    git clone git@github.com:LoueyDenden/spring_project_certifications.git temp_repo
                                    cd temp_repo/project_charts/charts/discovery
                                    sed -i '/^  tag: / s/: .*/: "${BUILD_ID}"/' values.yaml
                                    git config user.name "Louey Denden"
                                    git config user.email "louey.denden@polytechnicien.tn"
                                    git add values.yaml
                                    git commit -m "CustomUpdated tag to ${BUILD_ID} in helm chart"
                                    git push origin main
                                    cd ../../../..
                                    rm -R temp_repo
                                    """
                                    }
                        }
                    }
            }
        stage('Push Gateway Image to Docker Hub') {
            //when { changeset "gateway/**"}
                steps {
                    script {
                                docker.withRegistry('https://index.docker.io/v1/', 'dockerhub') {
                                        dockerImageGateway.push()
                                }
                            sh 'echo "UPDATING TAG IN HELM CHARTS"'
                            withCredentials([sshUserPrivateKey(credentialsId: 'github_key', keyFileVariable: 'SSH_KEY')]) {
                                sh """
                                    git clone git@github.com:LoueyDenden/spring_project_certifications.git temp_repo
                                    cd temp_repo/project_charts/charts/gateway
                                    sed -i '/^  tag: / s/: .*/: "${BUILD_ID}"/' values.yaml
                                    git config user.name "Louey Denden"
                                    git config user.email "louey.denden@polytechnicien.tn"
                                    git add values.yaml
                                    git commit -m "CustomUpdated tag to ${BUILD_ID} in helm chart"
                                    git push origin main
                                    cd ../../../..
                                    rm -R temp_repo
                                    """
                                    }
                        }
                    }
            }
        stage('Push Library Service Image to Docker Hub') {
            //when { changeset "library-service/**"}
                steps {
                    script {
                                docker.withRegistry('https://index.docker.io/v1/', 'dockerhub') {
                                        dockerImageLibraryService.push()
                                }
                            sh 'echo "UPDATING TAG IN HELM CHARTS"'
                            withCredentials([sshUserPrivateKey(credentialsId: 'github_key', keyFileVariable: 'SSH_KEY')]) {
                                sh """
                                    git clone git@github.com:LoueyDenden/spring_project_certifications.git temp_repo
                                    cd temp_repo/project_charts/charts/library
                                    sed -i '/^  tag: / s/: .*/: "${BUILD_ID}"/' values.yaml
                                    git config user.name "Louey Denden"
                                    git config user.email "louey.denden@polytechnicien.tn"
                                    git add values.yaml
                                    git commit -m "CustomUpdated tag to ${BUILD_ID} in helm chart"
                                    git push origin main
                                    cd ../../../..
                                    rm -R temp_repo
                                    """
                                    }
                        }
                    }
            }
        stage('Push User Service Image to Docker Hub') {
            //when { changeset "user-service/**"}
                steps {
                    script {
                                docker.withRegistry('https://index.docker.io/v1/', 'dockerhub') {
                                        dockerImageUserService.push()
                                }
                             sh 'echo "UPDATING TAG IN HELM CHARTS"'
                            withCredentials([sshUserPrivateKey(credentialsId: 'github_key', keyFileVariable: 'SSH_KEY')]) {
                                sh """
                                    git clone git@github.com:LoueyDenden/spring_project_certifications.git temp_repo
                                    cd temp_repo/project_charts/charts/user
                                    sed -i '/^  tag: / s/: .*/: "${BUILD_ID}"/' values.yaml
                                    git config user.name "Louey Denden"
                                    git config user.email "louey.denden@polytechnicien.tn"
                                    git add values.yaml
                                    git commit -m "CustomUpdated tag to ${BUILD_ID} in helm chart"
                                    git push origin main
                                    cd ../../../..
                                    rm -R temp_repo
                                    """
                            }
                        }
                    }
            }
        stage('Push Certifications Service Image to Docker Hub') {
            //when { changeset "certifications-service/**"}
                steps {
                    script {
                                docker.withRegistry('https://index.docker.io/v1/', 'dockerhub') {
                                        dockerImageCertificationsService.push()
                                }
                            sh 'echo "UPDATING TAG IN HELM CHARTS"'
                            withCredentials([sshUserPrivateKey(credentialsId: 'github_key', keyFileVariable: 'SSH_KEY')]) {
                                sh """
                                    git clone git@github.com:LoueyDenden/spring_project_certifications.git temp_repo
                                    cd temp_repo/project_charts/charts/certifications
                                    sed -i '/^  tag: / s/: .*/: "${BUILD_ID}"/' values.yaml
                                    git config user.name "Louey Denden"
                                    git config user.email "louey.denden@polytechnicien.tn"
                                    git add values.yaml
                                    git commit -m "CustomUpdated tag to ${BUILD_ID} in helm chart"
                                    git push origin main
                                    cd ../../../..
                                    rm -R temp_repo
                                    """
                                    }
                        }
                    }
            }
        stage('Push Order Service Image to Docker Hub') {
            //when { changeset "order-service/**"}
                steps {
                    script {
                                docker.withRegistry('https://index.docker.io/v1/', 'dockerhub') {
                                        dockerImageOrderService.push()
                                }
                            sh 'echo "UPDATING TAG IN HELM CHARTS"'
                            withCredentials([sshUserPrivateKey(credentialsId: 'github_key', keyFileVariable: 'SSH_KEY')]) {
                                sh """
                                    git clone git@github.com:LoueyDenden/spring_project_certifications.git temp_repo
                                    cd temp_repo/project_charts/charts/order
                                    sed -i '/^  tag: / s/: .*/: "${BUILD_ID}"/' values.yaml
                                    git config user.name "Louey Denden"
                                    git config user.email "louey.denden@polytechnicien.tn"
                                    git add values.yaml
                                    git commit -m "CustomUpdated tag to ${BUILD_ID} in helm chart"
                                    git push origin main
                                    cd ../../../..
                                    rm -R temp_repo
                                    """
                                    }
                        }
                    }
            }
        stage('Push FRONTEND Image to Docker Hub') {
            //when { changeset "UI_Spring/**"}
                steps {
                    script {
                                docker.withRegistry('https://index.docker.io/v1/', 'dockerhub') {
                                        dockerImageFront.push()
                                }
                            sh 'echo "UPDATING TAG IN HELM CHARTS"'
                            withCredentials([sshUserPrivateKey(credentialsId: 'github_key', keyFileVariable: 'SSH_KEY')]) {
                                sh """
                                    git clone git@github.com:LoueyDenden/spring_project_certifications.git temp_repo
                                    cd temp_repo/project_charts/charts/client
                                    sed -i '/^  tag: / s/: .*/: "${BUILD_ID}"/' values.yaml
                                    git config user.name "Louey Denden"
                                    git config user.email "louey.denden@polytechnicien.tn"
                                    git add values.yaml
                                    git commit -m "CustomUpdated tag to ${BUILD_ID} in helm chart"
                                    git push origin main
                                    cd ../../../..
                                    rm -R temp_repo
                                    """
                                    }
                        }
                    }
            }
    }
    //CLEAN UP !!
    post {
        always {
            script {
                if (env.SKIP_PIPELINE == "true") {
                    echo "Pipeline was skipped. No post actions required."
                } else {
                    echo "Pipeline completed. Running post actions."
                    echo 'Cleanup phase!'
                    def imagesToCleanup = [
                        'aquasec/trivy',
                        "${IMAGE_NAME_CONFIG_SERVER}",
                        "${IMAGE_NAME_DISCOVERY_SERVICE}",
                        "${IMAGE_NAME_GATEWAY}",
                        "${IMAGE_NAME_USER_SERVICE}",
                        "${IMAGE_NAME_CERTIFICATIONS_SERVICE}",
                        "${IMAGE_NAME_ORDER_SERVICE}",
                        "${IMAGE_NAME_LIBRARY_SERVICE}",
                        "${IMAGE_NAME_FRONTEND}" // Fix typo to IMAGE_NAME_FRONTEND if needed
                    ]
                    imagesToCleanup.each { imageName ->
                        def imageIds = sh(script: "docker images --filter=reference='${imageName}:*' -q", returnStdout: true).trim()
                        if (imageIds) {
                            imageIds.split('\n').each { imageId ->
                                sh "docker rmi -f ${imageId}"
                            }
                        }
                    }
                    def dirExists = fileExists('temp_repo')
                    if (dirExists) {
                        deleteDir()
                    }
                    echo 'Cleanup Successfully done!'
                }
            }
        }
    }
}
