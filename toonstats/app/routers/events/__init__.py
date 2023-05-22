import asyncio

from fastapi import APIRouter
from loguru import logger
import aiohttp

from toonstats.database import ToonDatabase
from toonstats.toonhq.scraper import ToonHQScraper
from toonstats.settings import DatabaseSettings


SLEEP = 60


class EventsRouter(APIRouter):
    def __init__(self):
        self.__scrape_task: asyncio.Task | None = None

        super().__init__(on_startup=[self.__startup], on_shutdown=[self.__shutdown])

    async def __startup(self) -> None:
        logger.info('Starting up scraper...')
        self.__scrape_task = asyncio.create_task(scrape())

    async def __shutdown(self) -> None:
        logger.info('Shutting down scraper...')

        if self.__scrape_task is not None:
            self.__scrape_task.cancel()


async def scrape():
    settings = DatabaseSettings()

    async with ToonDatabase(
        settings.db_url, 
        settings.db_username, 
        settings.db_password, 
        settings.db_name
    ) as db, aiohttp.ClientSession() as session:
        
        scraper = ToonHQScraper(session)
        
        while True:
            toons = await scraper.scrape()
            await db.upsert_toons(toons)
    
            logger.info('Sleeping for {} seconds', SLEEP)
            await asyncio.sleep(SLEEP)


router = EventsRouter()