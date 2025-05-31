'use client';

import { TooltipProvider } from '@/components/ui/tooltip';
import { SWRConfig } from 'swr';
import { fetcher } from '@/lib/fetchers';

export default function Providers({ children }: { children: React.ReactNode }) {
  return <TooltipProvider>{children}</TooltipProvider>;
}

export function SWRProvider({ children }: { children: React.ReactNode }) {
  return (
      <SWRConfig
          value={{
            fetcher,
            refreshInterval: 3000,
          }}
      >
        {children}
      </SWRConfig>
  );
}
