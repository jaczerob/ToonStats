import json
import re

import aiohttp
from loguru import logger

from toonstats.toonhq.models import ToonHQToon


__all__ = ['ToonHQScraper']


TOONHQ_PATTERN = re.compile('\"toon\": (.+?})', re.DOTALL)


PRESTIGE_MAP = {
    'toonup': 1,
    'trap': 2,
    'lure': 3,
    'sound': 4,
    'throw': 5,
    'squirt': 6,
    'drop': 7
}


class ToonHQScraper:
    def __init__(self, session: aiohttp.ClientSession) -> None:
        self.__session: aiohttp.ClientSession = session

    async def __get_toonhq_data(self) -> bytes:
        async with self.__session.get('https://toonhq.org/groups') as resp:
            return await resp.read()
            
    async def __parse_toonhq_data(self, body: bytes) -> list[ToonHQToon]:
        raw_toons = TOONHQ_PATTERN.findall(body.decode('utf-8'))
        parsed_toons = []

        for raw_toon in raw_toons:
            json_toon = json.loads(raw_toon)

            if 'id' not in json_toon:
                # not sure why ToonHQ doesn't have an ID for some toons
                continue
            elif json_toon['game'] != 1:
                # we hate clash toons
                continue
            elif json_toon['photo'] is None:
                # means the toon is not synced
                continue

            # convert prestiges to a ttr organic
            if len(json_toon['prestiges']) > 0:
                json_toon['organic'] = PRESTIGE_MAP[json_toon['prestiges'][0]]
            else:
                json_toon['organic'] = 0

            # remove unnecessary fields
            json_toon.pop('game')
            json_toon.pop('name')
            json_toon.pop('color')
            json_toon.pop('label')
            json_toon.pop('photo')
            json_toon.pop('photo_bg')
            json_toon.pop('zap')
            json_toon.pop('prestiges')

            logger.info('Parsed toon: {}', json_toon)
            parsed_toons.append(ToonHQToon(**json_toon))

        return parsed_toons

    async def scrape(self) ->list[ToonHQToon]:
        body = await self.__get_toonhq_data()
        return await self.__parse_toonhq_data(body)


async def main():
    async with aiohttp.ClientSession() as session:
        scraper = ToonHQScraper(session)
        for toon in await scraper.scrape():
            print(toon)


if __name__ == '__main__':
    import asyncio
    asyncio.run(main())
