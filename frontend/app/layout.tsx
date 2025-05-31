import './globals.css';
import {SWRProvider} from "./providers";

export const metadata = {
    title: 'Athletica CRM',
    description: 'CRM для спортивных организаций'
};

export default function RootLayout({children}: { children: React.ReactNode; }) {
    return (
        <html lang="en">
        <body className="flex min-h-screen w-full flex-col">
        <SWRProvider>
            {children}
        </SWRProvider>
        </body>
        </html>
    );
}
