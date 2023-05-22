from dataclasses import dataclass


__all__ = ['ToonHQToon']


@dataclass
class ToonHQToon:
    id: int
    species: int
    laff: int
    toonup: int
    trap: int
    lure: int
    sound: int
    throw: int
    squirt: int
    drop: int
    organic: int
    sellbot: int
    cashbot: int
    lawbot: int
    bossbot: int

    def to_tuple(self) -> tuple[int, ...]:
        return (
            self.id,
            self.species,
            self.laff,
            self.toonup,
            self.trap,
            self.lure,
            self.sound,
            self.throw,
            self.squirt,
            self.drop,
            self.organic,
            self.sellbot,
            self.cashbot,
            self.lawbot,
            self.bossbot
        )
