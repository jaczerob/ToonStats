from toonstats.models import Toon


class AbstractToonEstateManager:
    async def __aenter__(self) -> 'AbstractToonEstateManager':
        raise NotImplementedError
    
    async def __aexit__(self, exc_type, exc, tb) -> None:
        raise NotImplementedError

    async def store_toons(self, toons: list[Toon]) -> None:
        raise NotImplementedError
