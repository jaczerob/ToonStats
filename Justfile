set dotenv-load

install:
    poetry update && poetry install
    poetry lock

run:
    poetry run uvicorn toonstats.app.main:app --port 8080 --host '::' --workers 1

mypy:
    mypy --python-executable=.venv/bin/python toonstats

docker:
    docker-compose build
    docker-compose push