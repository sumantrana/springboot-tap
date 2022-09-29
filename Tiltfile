SOURCE_IMAGE = os.getenv("SOURCE_IMAGE", default='harbor-repo.vmware.com/vspheretmm/springboot-maven-source')
LOCAL_PATH = os.getenv("LOCAL_PATH", default='.')
NAMESPACE = os.getenv("NAMESPACE", default='default')
OUTPUT_TO_NULL_COMMAND = os.getenv("OUTPUT_TO_NULL_COMMAND", default=' > /dev/null ')

k8s_custom_deploy(
    'springboot-maven',
    apply_cmd="tanzu apps workload apply -f workload.yaml --debug --live-update" +
        " --local-path " + LOCAL_PATH +
        " --source-image " + SOURCE_IMAGE +
        " --namespace " + NAMESPACE +
        " --yes " +
        OUTPUT_TO_NULL_COMMAND + 
        " && kubectl get workload springboot-maven --namespace " + NAMESPACE + " -o yaml",
    delete_cmd="tanzu apps workload delete -f workload.yaml --namespace " + NAMESPACE + " --yes" ,
    deps=['pom.xml', './target/classes'],
    container_selector='workload',
    live_update=[
        sync('./target/classes', '/workspace/BOOT-INF/classes')
    ]
)

k8s_resource('springboot-maven', port_forwards=["8080:8080"],
    extra_pod_selectors=[{'serving.knative.dev/service': 'springboot-maven'}])

allow_k8s_contexts('tap-ga')