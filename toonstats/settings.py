from pydantic import BaseSettings, Field


class DatabaseSettings(BaseSettings):
    db_url: str = Field(..., env='HOST')
    db_username: str = Field(..., env='USERNAME')
    db_password: str = Field(..., env='PASSWORD')
    db_name: str = Field(..., env='DATABASE')
