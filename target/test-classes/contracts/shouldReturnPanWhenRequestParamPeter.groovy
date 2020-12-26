import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return peter when request param is pan"
   request {
        method GET()
        url("/hello/Pan") {
        }
    }
    response {
        body("Who is this 'Pan' you're talking about?")
        status 200
    }
}