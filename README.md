# SpringBoot sample app using maven package manager

Designed to illustrate how buildpacks and supply chains work to build and deploy an application on Kubernetes. Should work just fine with VMware Tanzu Application Platform and VMware Tanzu Application Service.

## Running Locally

`./mvnw spring-boot:run`

## Viewing

`curl http://localhost:8080`

## Running on TAP

```bash
tanzu apps workload create springboot-maven \
  --git-repo https://github.com/benwilcock/springboot-maven \
  --git-branch main \
  --type web \
  --build-env BP_JVM_VERSION=17 \
  --label app.kubernetes.io/part-of=springboot-maven \
  --label apps.tanzu.vmware.com/has-tests=true \
  --param-yaml testing_pipeline_matching_labels='{"apps.tanzu.vmware.com/pipeline":"test", "apps.tanzu.vmware.com/language":"java"}' \
  --annotation autoscaling.knative.dev/minScale=1 \
  --namespace default \
  --tail \
  --yes
```

## Application Endpoints

1. `/`  HTML home page (shows a single page app containing a static image and some text). Contains a link to the source code.
1. `/messages` REST Json [GET] (shows a single hardcoded message as part of a list of messages).
1. `/actuator` REST Json [GET] (Links to the Heath indicator).

## Customisations

For a simple customisation, in the application code (in the `arc/main/resources/application.yml` file) change the name of the `client` property from "VMware" to something else and then commit/redeploy/restart.

```yaml
client: "VMware"
```

The homepage will then use the new name of the client in the text at the bottom of the page.

## Vulnerability Scanning

Adding a known vulnerability:

Version 2.7.4 of Spring Boot Starter Actuator includes a vulnerable version of Snake YAML (1.3.0). Add this to the `POM.xml` file.

```xml
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
```

Removing the vulnerability:

Upgrade the Snake YAML dependency manually by adding this to the `POM.xml` file.

```xml
		<dependency>
			<groupId>org.yaml</groupId>
			<artifactId>snakeyaml</artifactId>
			<version>1.32</version>
		</dependency>
```