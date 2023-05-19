import asyncio
import os

from toonstats.database import ToonDatabase
from toonstats.toonhq.scraper import ToonHQScraper

import aiohttp
from fastapi import FastAPI
from loguru import logger


app = FastAPI()


@app.get('/')
async def root() -> str:
    return 'Come back later.'


@app.get('/health')
async def health() -> bool:
    return True


@app.on_event('startup')
async def app_startup():
    logger.info('Starting up...')
    app.state.running = True

    asyncio.create_task(scrape())


@app.on_event('shutdown')
async def app_shutdown():
    logger.info('Shutting down...')
    app.state.running = False


async def scrape():
    SLEEP = 60

    async with ToonDatabase(
        os.environ.get('HOST', 'localhost'),
        os.environ.get('USERNAME', 'root'),
        os.environ.get('PASSWORD', ''),
        os.environ.get('DATABASE', 'toonstats'),
    ) as db, aiohttp.ClientSession() as session:
        
        scraper = ToonHQScraper(session)
        
        while app.state.running:
            toons = await scraper.scrape()
            await db.upsert_toons(toons)
    
            logger.info('Sleeping for {} seconds', SLEEP)
            await asyncio.sleep(SLEEP)
