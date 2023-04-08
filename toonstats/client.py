import asyncio
import logging
import os

from toonstats.estates import AbstractToonEstateManager
from toonstats.finders import AbstractToonFinder


SLEEP_TIME = int(os.environ.get('SLEEP_TIME'))


class ToonStatsClient:
    def __init__(self, estate_manager: AbstractToonEstateManager, toon_finder: AbstractToonFinder) -> None:
        self.__estate_manager = estate_manager
        self.__toon_finder = toon_finder

    async def mainloop(self) -> None:
        while True:
            try:
                toons = await self.__toon_finder.find_toons()
                await self.__estate_manager.store_toons(toons)
                await asyncio.sleep(SLEEP_TIME)
            except KeyboardInterrupt:
                return
            except Exception as e:
                logging.exception('Exception in mainloop', exc_info=e, extra={'exception': e})
                return