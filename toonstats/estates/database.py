from typing import TYPE_CHECKING
import logging
import os

import databases

from toonstats.models import Toon


if TYPE_CHECKING:
    from toonstats.estates import AbstractToonEstateManager


DATABASE_URL = '{}://{}:{}@{}/{}?ssl=true'.format(
    os.environ.get('DATABASE_DRIVER'),
    os.environ.get('DATABASE_USER'),
    os.environ.get('DATABASE_PASSWORD'),
    os.environ.get('DATABASE_HOST'),
    os.environ.get('DATABASE_DATABASE')
)

UPSERT_QUERY = '''INSERT INTO toons (id, species, laff, gag_toonup, gag_trap, gag_lure, gag_sound, gag_throw, gag_squirt, gag_drop, organic, sellbot, cashbot, lawbot, bossbot)
                   VALUES (:id, :species, :laff, :gag_toonup, :gag_trap, :gag_lure, :gag_sound, :gag_throw, :gag_squirt, :gag_drop, :organic, :sellbot, :cashbot, :lawbot, :bossbot)
                   ON DUPLICATE KEY UPDATE
                   species = :species,
                   laff = :laff,
                   gag_toonup = :gag_toonup,
                   gag_trap = :gag_trap,
                   gag_lure = :gag_lure,
                   gag_sound = :gag_sound,
                   gag_throw = :gag_throw,
                   gag_squirt = :gag_squirt,
                   gag_drop = :gag_drop,
                   organic = :organic,
                   sellbot = :sellbot,
                   cashbot = :cashbot,
                   lawbot = :lawbot,
                   bossbot = :bossbot;'''


database = databases.Database(DATABASE_URL)


class DatabaseToonEstateManager:
    async def __aenter__(self):
        await database.connect()
        return self

    async def __aexit__(self, exc_type, exc, tb) -> None:
        await database.disconnect()

    async def store_toons(self, toons: list[Toon]) -> None:
        values = [toon.to_dict() for toon in toons]

        try:
            await database.execute_many(query=UPSERT_QUERY, values=values)
            logging.info('Stored %d toons', len(toons))
        except Exception as e:
            logging.exception('Failed to store toons', exc_info=e, extra={'toons': values, 'exception': e})