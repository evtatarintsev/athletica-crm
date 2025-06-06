'use client';

import Link from 'next/link';
import {Home, LineChart, Package, PanelLeft, ShoppingCart, Users2} from 'lucide-react';

import {Sheet, SheetContent, SheetTrigger} from '@/components/ui/sheet';
import {Logo} from '@/components/icons';
import {Button} from "@mui/material";

export default function MobileNav() {
    return (
        <Sheet>
            <SheetTrigger asChild>
                <div className="hidden sm:block">
                    <Button size="small" variant="outlined">
                        <PanelLeft className="h-5 w-5"/>
                        <span className="sr-only">Toggle Menu</span>
                    </Button>
                </div>
            </SheetTrigger>
            <SheetContent side="left" className="sm:max-w-xs">
                <nav className="grid gap-6 text-lg font-medium">
                    <Link
                        href="/"
                        className="group flex h-10 w-10 shrink-0 items-center justify-center gap-2 rounded-full bg-primary text-lg font-semibold text-primary-foreground md:text-base"
                    >
                        <Logo/>
                        <span className="sr-only">Vercel</span>
                    </Link>
                    <Link
                        href="/"
                        className="flex items-center gap-4 px-2.5 text-muted-foreground hover:text-foreground"
                    >
                        <Home className="h-5 w-5"/>
                        Главная
                    </Link>
                    <Link
                        href="#"
                        className="flex items-center gap-4 px-2.5 text-muted-foreground hover:text-foreground"
                    >
                        <ShoppingCart className="h-5 w-5"/>
                        Заказы
                    </Link>
                    <Link
                        href="#"
                        className="flex items-center gap-4 px-2.5 text-foreground"
                    >
                        <Package className="h-5 w-5"/>
                        Товары
                    </Link>
                    <Link
                        href="#"
                        className="flex items-center gap-4 px-2.5 text-muted-foreground hover:text-foreground"
                    >
                        <Users2 className="h-5 w-5"/>
                        Клиенты
                    </Link>
                    <Link
                        href="/"
                        className="flex items-center gap-4 px-2.5 text-muted-foreground hover:text-foreground"
                    >
                        <LineChart className="h-5 w-5"/>
                        Аналитика
                    </Link>
                </nav>
            </SheetContent>
        </Sheet>
    );
}