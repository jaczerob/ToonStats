from dataclasses import dataclass

from dataclasses_json import dataclass_json


@dataclass_json
@dataclass
class Toon:
    id: int
    species: int
    laff: int
    gag_toonup: int
    gag_trap: int
    gag_lure: int
    gag_sound: int
    gag_throw: int
    gag_squirt: int
    gag_drop: int
    organic: int
    sellbot: int
    cashbot: int
    lawbot: int
    bossbot: int
