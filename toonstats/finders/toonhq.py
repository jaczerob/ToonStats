from typing import TYPE_CHECKING
import logging
import json
import re

import aiohttp

from toonstats.models import Toon


if TYPE_CHECKING:
    from toonstats.finders import AbstractToonFinder


TOONS_URL = 'https://toonhq.org/groups/'
TOONS_REGEX = re.compile(r'"toon": (.+?})', re.S)
ORGANIC_LOOKUP = {
    'none': 0,
    'toonup': 1,
    'trap': 2,
    'lure': 3,
    'sound': 4,
    'throw': 5,
    'squirt': 6,
    'drop': 7
}


class ToonHQToonFinder:
    def __init__(self) -> None:
        self.__session: aiohttp.ClientSession = None
        
    async def __aenter__(self):
        self.__session = aiohttp.ClientSession()
        return self
    
    async def __aexit__(self, exc_type, exc, tb):
        await self.__session.close()

    async def find_toons(self):
        async with self.__session.get(TOONS_URL) as resp:
            text = await resp.text()
            unparsed_toons = TOONS_REGEX.findall(text)
            
        parsed_toons = []

        for toon in unparsed_toons:
            if parsed_toon := parse_toon(toon):
                parsed_toons.append(parsed_toon)
        
        return parsed_toons
    

def parse_toon(json_string: str) -> Toon | None:
    try:
        json_toon = json.loads(json_string)
    except Exception as e:
        logging.exception('Failed to parse toon: %s', json_string, exc_info=e)
        return None
    
    if 'id' not in json_toon:
        # I am not sure why some toons do not have IDs...
        return None
    
    elif 'game' not in json_toon:
        logging.warning('Could not find game in toon', extra={'toon': json_toon})
        return None

    elif json_toon.get('game') != 1:
        # this is a clash toon
        return None
    
    elif json_toon.get('photo') is None:
        # this toon is probably not synced?
        logging.info('Found potentially unsynced toon', extra={'toon': json_toon})
        return None
    
    try:
        return Toon(
            id=int(json_toon.get('id')),
            species=int(json_toon.get('species')),
            laff=int(json_toon.get('laff')),
            gag_toonup=int(json_toon.get('toonup')),
            gag_trap=int(json_toon.get('trap')),
            gag_lure=int(json_toon.get('lure')),
            gag_sound=int(json_toon.get('sound')),
            gag_throw=int(json_toon.get('throw')),
            gag_squirt=int(json_toon.get('squirt')),
            gag_drop=int(json_toon.get('drop')),
            organic=ORGANIC_LOOKUP.get(json_toon.get('organic')[0] if json_toon.get('organic') else 'none'),
            sellbot=int(json_toon.get('sellbot')),
            cashbot=int(json_toon.get('cashbot')),
            lawbot=int(json_toon.get('lawbot')),
            bossbot=int(json_toon.get('bossbot')),
        )
    except Exception as e:
        logging.exception('Failed to parse toon', exc_info=e, extra={'toon': json_toon, 'exception': e})
        return None