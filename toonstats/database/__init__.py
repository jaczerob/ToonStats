import aiomysql
from loguru import logger

from toonstats.toonhq.models import ToonHQToon


__all__ = ['ToonDatabase']


UPSERT_QUERY = '''INSERT INTO toons (
    id,
    species,
    laff,
    gag_toonup,
    gag_trap,
    gag_lure,
    gag_sound,
    gag_throw,
    gag_squirt,
    gag_drop,
    organic,
    sellbot,
    cashbot,
    lawbot,
    bossbot
)
VALUES (
    %s,
    %s,
    %s,
    %s,
    %s,
    %s,
    %s,
    %s,
    %s,
    %s,
    %s,
    %s,
    %s,
    %s,
    %s
)
ON DUPLICATE KEY UPDATE
    species = VALUES(species),
    laff = VALUES(laff),
    gag_toonup = VALUES(gag_toonup),
    gag_trap = VALUES(gag_trap),
    gag_lure = VALUES(gag_lure),
    gag_sound = VALUES(gag_sound),
    gag_throw = VALUES(gag_throw),
    gag_squirt = VALUES(gag_squirt),
    gag_drop = VALUES(gag_drop),
    organic = VALUES(organic),
    sellbot = VALUES(sellbot),
    cashbot = VALUES(cashbot),
    lawbot = VALUES(lawbot),
    bossbot = VALUES(bossbot);
'''


class ToonDatabase:
    def __init__(self, host: str, username: str, password: str, database: str) -> None:
        self.__host: str = host
        self.__username: str = username
        self.__password: str = password
        self.__database: str = database

        self.__conn: aiomysql.Connection = None

    async def __aenter__(self) -> 'ToonDatabase':
        self.__conn = await aiomysql.connect(
            host=self.__host,
            user=self.__username, 
            password=self.__password, 
            db=self.__database,
            ssl=True
        )

        return self
    
    async def __aexit__(self, *_) -> None:
        if self.__conn is not None:
            self.__conn.close()

    async def upsert_toons(self, toons: list[ToonHQToon]) -> None:
        async with self.__conn.cursor() as cursor:
            logger.info('Upserting toons: {}', len(toons))
            await cursor.executemany(UPSERT_QUERY, [toon.to_tuple() for toon in toons])
            await cursor.execute('COMMIT;')