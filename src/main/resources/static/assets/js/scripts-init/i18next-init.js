
document.addEventListener('DOMContentLoaded', function() {

    const currentLang = localStorage.getItem('language') || 'en';

    i18next
        .use(i18nextHttpBackend)
        .init({
            lng: currentLang,
            fallbackLng: 'en',
            debug: false,
            backend: {
                loadPath: '/locales/{{lng}}/{{ns}}.json',
            },
            ns: ['translation'],
            defaultNS: 'translation'
        }, function(err, t) {
            if (err) return console.error('Something went wrong loading translations', err);

            updateContent();
            setupLanguageSwitcher();
            updateLanguageFlag(currentLang);
        });

    function updateContent() {
        document.querySelectorAll('[data-i18n]').forEach(function(element) {
            const key = element.getAttribute('data-i18n');
            if (key.startsWith('[placeholder]')) {
                const attrKey = key.substring(12);
                element.setAttribute('placeholder', i18next.t(attrKey));
            } else {
                element.textContent = i18next.t(key);
            }
        });
    }

    function updateLanguageFlag(lang) {
        const headerFlag = document.querySelector('.header-flag');
        if (headerFlag) {
            // Remove all country classes
            headerFlag.classList.remove('US', 'RU', 'UA');
            
            // Add appropriate country class based on language
            if (lang === 'en') headerFlag.classList.add('US');
            else if (lang === 'ru') headerFlag.classList.add('RU');
            else if (lang === 'uk') headerFlag.classList.add('UA');
        }
    }

    function setupLanguageSwitcher() {
        const languageSwitchers = document.querySelectorAll('.language-switcher');
        
        languageSwitchers.forEach(function(switcher) {
            switcher.addEventListener('click', function(e) {
                e.preventDefault();
                const lang = this.getAttribute('data-lang');
                
                // Save selected language to localStorage
                localStorage.setItem('language', lang);
                
                i18next.changeLanguage(lang, function() {
                    updateContent();
                    updateLanguageFlag(lang);
                });
            });
        });
    }
}); 