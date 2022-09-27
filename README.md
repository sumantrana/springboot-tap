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
  --label app.kubernetes.io/part-of=springboot-maven \
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

Open the Pipfile, and under the `[Packages]` section add the line `ffmpeg = "==1.4"`.

```bash
nano Pipfile
```

Now recreate the `requirements.txt` file (Grype uses this as a list of dependencies):

```bash
pipenv lock --requirements > requirements.txt
```

Now run the Grype scanner on the code folder:

```bash
grype . # Run from this folder
```

Grype should spot the vulnerability and log warnings to the console.

To remove the vulnerability, remove the ffmpeg dependency from the `Pipfile` and recreate the `requirements.txt` again.