from dotenv import load_dotenv
load_dotenv()

import asyncio
import logging.config

import yaml

from toonstats.client import ToonStatsClient
from toonstats.estates.database import DatabaseToonEstateManager
from toonstats.finders.toonhq import ToonHQToonFinder


with open('logging.yml', 'r') as f:
    log_config = yaml.safe_load(f)


logging.config.dictConfig(log_config)


async def main():
    async with \
        ToonHQToonFinder() as toon_finder, \
        DatabaseToonEstateManager() as estate_manager:

        client = ToonStatsClient(estate_manager, toon_finder)

        await client.mainloop()


if __name__ == '__main__':
    loop = asyncio.get_event_loop()
    loop.run_until_complete(main())