from toonstats.models import Toon


class AbstractToonFinder:
    async def __aenter__(self) -> 'AbstractToonFinder':
        raise NotImplementedError
    
    async def __aexit__(self, exc_type, exc, tb) -> None:
        raise NotImplementedError

    async def find_toons(self) -> list[Toon]:
        raise NotImplementedError
    
