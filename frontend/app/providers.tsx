import {SWRConfig} from 'swr';
import fetcher from '@/lib/fetchers';

export function SWRProvider({children}: { children: React.ReactNode }) {
    return (
        <SWRConfig value={{fetcher, refreshInterval: 3000}}>
            {children}
        </SWRConfig>
    );
}
