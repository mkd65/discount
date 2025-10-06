import {
    enableProdMode, Injector, PLATFORM_INITIALIZER, StaticProvider,
} from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { AppModule } from './app/app.module';

export function isIE(): boolean {
    const ua = window.navigator.userAgent;

    const msie = ua.indexOf('MSIE ');
    const ie11 = ua.indexOf('Trident');
    return (msie > 0 || ie11 > 0);
}

export const checkBrowser = (injector: Injector /* , service: DsBrowserService, */) => (): void => {
    if (isIE() && !window.location.href.includes('erreur/code/IE')) {
        window.location.href = '#/erreur/code/IE';
    }
};


export const BROWSER_PROVIDER: StaticProvider[] = [
    {
        provide: PLATFORM_INITIALIZER,
        useFactory: checkBrowser,
        deps: [],
        multi: true,
    },
];

platformBrowserDynamic(BROWSER_PROVIDER).bootstrapModule(AppModule, {
    providers: [],
}).catch((err) => err);
