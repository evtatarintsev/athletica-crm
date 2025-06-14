import Link from 'next/link';
import {Home, LineChart, Package, Settings, ShoppingCart, Users2} from 'lucide-react';
import {Tooltip, TooltipContent, TooltipTrigger} from '@/components/ui/tooltip';
import {User} from './user';
import {Logo} from '@/components/icons';
import Providers from './providers';
import {NavItem} from './nav-item';
import {SearchInput} from './search';
import {BreadcrumbLink, BreadcrumbPage, Breadcrumbs} from "@/components/ui/breadcrumbs";
import MobileNav from "./mobileMenu";

export default function DashboardLayout({
                                            children
                                        }: {
    children: React.ReactNode;
}) {
    return (
        <Providers>
            <main className="flex min-h-screen w-full flex-col bg-muted/40">
                <DesktopNav/>
                <div className="flex flex-col sm:gap-4 sm:py-4 sm:pl-14">
                    <header
                        className="sticky top-0 z-30 flex h-14 items-center gap-4 border-b bg-background px-4 sm:static sm:h-auto sm:border-0 sm:bg-transparent sm:px-6">
                        <MobileNav/>
                        <DashboardBreadcrumb/>
                        <SearchInput/>
                        <User/>
                    </header>
                    <main className="grid flex-1 items-start gap-2 p-4 sm:px-6 sm:py-0 md:gap-4 bg-muted/40">
                        {children}
                    </main>
                </div>
            </main>
        </Providers>
    );
}

function DesktopNav() {
    return (
        <aside className="fixed inset-y-0 left-0 z-10 hidden w-14 flex-col border-r bg-background sm:flex">
            <nav className="flex flex-col items-center gap-4 px-2 sm:py-5">
                <Link
                    href="/"
                    className="group flex h-9 w-9 shrink-0 items-center justify-center gap-2 rounded-full bg-primary text-lg font-semibold text-primary-foreground md:h-8 md:w-8 md:text-base"
                >
                    <Logo/>
                    <span className="sr-only">Athletica CRM</span>
                </Link>

                <NavItem href="/" label="Главная">
                    <Home className="h-5 w-5"/>
                </NavItem>

                <NavItem href="/customers" label="Клиенты">
                    <Users2 className="h-5 w-5"/>
                </NavItem>

                <NavItem href="/" label="Заказы">
                    <ShoppingCart className="h-5 w-5"/>
                </NavItem>

                <NavItem href="/" label="Товары">
                    <Package className="h-5 w-5"/>
                </NavItem>

                <NavItem href="/" label="Аналитика">
                    <LineChart className="h-5 w-5"/>
                </NavItem>
            </nav>
            <nav className="mt-auto flex flex-col items-center gap-4 px-2 sm:py-5">
                <Tooltip>
                    <TooltipTrigger asChild>
                        <Link
                            href="/"
                            className="flex h-9 w-9 items-center justify-center rounded-lg text-muted-foreground transition-colors hover:text-foreground md:h-8 md:w-8"
                        >
                            <Settings className="h-5 w-5"/>
                            <span className="sr-only">Настройки</span>
                        </Link>
                    </TooltipTrigger>
                    <TooltipContent side="right">Настройки</TooltipContent>
                </Tooltip>
            </nav>
        </aside>
    );
}


function DashboardBreadcrumb() {
    return (
        <Breadcrumbs>
            <BreadcrumbLink title={'Главная'} href={'/'}/>
            <BreadcrumbLink title={'Products'} href={'/'}/>
            <BreadcrumbPage title={'All Products'}/>
        </Breadcrumbs>
    );
}
