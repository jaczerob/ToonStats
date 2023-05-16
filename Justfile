set dotenv-load

install:
    poetry update && poetry install
    poetry lock

run:
    poetry run python toonstats

mypy:
    mypy --python-executable=.venv/bin/python toonstats