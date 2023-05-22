from fastapi import FastAPI

from toonstats.app.routers import events, health, root


app = FastAPI()

app.include_router(events.router)
app.include_router(health.router)
app.include_router(root.router)


