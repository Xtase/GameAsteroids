##################################################
# dev
##################################################
dev_start:
	docker-compose up -d db admin back

dev_stop:
	docker-compose stop

dev_delete:
	docker-compose rm -fsv


##################################################
# Docker
##################################################

build_server:
	@make -C src/back

launch_server:
	@make -C src/back run


##################################################
# Docker
##################################################

docekr_run_db:
	docker-compose up -d db admin

docker_shell_db:
	docker exec db 

docker_cli_db:
	docker exec -it db psql -U Postgres

docker_create_database:
	docker exec db psql -U Postgres -c "CREATE DATABASE stockoverflow"

docker_log_db:
	docker logs -f db

docker_log_back:
	docker logs -f back

docker_log_front:
	docker logs -f front



##################################################
# Docker
##################################################

CONTAINER_NAME = back
REGISTRY = $(CONTAINER_NAME)

docker_compose_reload:
	docker-compose  -f ../../docker-compose.yml restart   $(CONTAINER_NAME)

# Build 
docker_build:
	docker build -t $(REGISTRY) .

# Build and launch Docker with the app
docker_run: docker_stop docker_rm docker_build
	docker run -d -p 5001:80 -v $(PWD):/go/src/app --name $(CONTAINER_NAME) $(REGISTRY):latest

docker_shell:
	docker exec -it $(CONTAINER_NAME) /bin/sh

docker_init_fixtures:
	docker exec -it $(CONTAINER_NAME) make init_fixtures

docker_run_without_build:  docker_stop docker_rm
	docker run -d -p 5001:80 -v $(PWD):/go/src/app --name $(CONTAINER_NAME) $(REGISTRY):latest

# Stop container
docker_stop:
	docker stop $(CONTAINER_NAME) || true

# Remove container
docker_rm:
	docker rm -f $(CONTAINER_NAME) || true

# Stop all docker containers
docker_fstop:
	docker stop $(docker ps -a -q)

# Build and Push the image to the gitlab registry
docker_push: docker_build docker_login
	docker push $(REGISTRY)

# Login docker with registry.gitlab.com
docker_login:
	docker login registry.gitlab.com

# Remove all docker containers and images from the machine (add -f to force delete)
docker_fclean:
	docker rm $(docker ps -a -q)
	docker rmi $(docker images -q)

#Logs
docker_log:
	docker logs -f $(CONTAINER_NAME)