import asyncio
import os

from toonstats.database import ToonDatabase
from toonstats.toonhq.scraper import ToonHQScraper

import aiohttp
from loguru import logger


SLEEP = 60


async def main():
    async with ToonDatabase(
        os.getenv('HOST'),
        os.getenv('USERNAME'),
        os.getenv('PASSWORD'),
        os.getenv('DATABASE')
    ) as db, aiohttp.ClientSession() as session:
        
        while True:
            toons = await ToonHQScraper(session).scrape()
            await db.upsert_toons(toons)
    
            logger.info('Sleeping for {} seconds', SLEEP)
            await asyncio.sleep(SLEEP)


if __name__ == '__main__':
    asyncio.run(main())