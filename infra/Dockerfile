FROM golang:latest

# GET ENVIRONMENT (debug, release) PASSED AS A COMMAND-LINE ARGUMENT
ARG ENV

# DEFINE ENVIRONNEMENT VARIABLES
# ENV APP=${ENV}

# RUN  apk update && apk upgrade && apk add watch

# SET WORKING DIRECTORY
WORKDIR /go/src/app

# ADD PROJECT
COPY . .

# BUILD APP
RUN make build

# BUILD GODOC Documentation
# TODO ADD GODOC

# TODO ADD GO TEST


# EXPOSE PORT 80
EXPOSE 80

# START THE APPLICATION
# ENTRYPOINT ["make", "run"]
CMD ["make", "run_with_docker"]