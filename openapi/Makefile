backend:
	openapi-generator generate \
    		-i openapi.yaml \
    		-g kotlin \
    		-o generated/backend \
    		--additional-properties=serializationLibrary=kotlinx_serialization,generateModelsOnly=true
frontend:
	openapi-generator generate \
    		-i openapi.yaml \
    		-g typescript \
    		-o generated/frontent \
    		--additional-properties=generateModelsOnly=true
