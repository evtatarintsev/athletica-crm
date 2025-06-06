"use client";

import { useEffect, useState } from "react";

// Компонент для рендеринга содержимого только на стороне клиента
// Это помогает избежать ошибок гидратации при использовании компонентов,
// которые могут по-разному рендериться на сервере и клиенте
export function ClientOnly({ children }: { children: React.ReactNode }) {
  const [isMounted, setIsMounted] = useState(false);

  useEffect(() => {
    setIsMounted(true);
  }, []);

  if (!isMounted) {
    return null;
  }

  return <>{children}</>;
}