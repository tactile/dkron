##Getting Started 

### Install Docker 

1. Download docker https://docs.docker.com/engine/install/
2. Start docker daemon

###Start dkron server 
Run the included Docker Compose config:

`docker-compose up`

This will start Dkron instances. To add more Dkron instances to the clusters:

```
docker-compose up --scale dkron-server=4
docker-compose up --scale dkron-agent=10
```

###Restore job state from archmaester 
TODO