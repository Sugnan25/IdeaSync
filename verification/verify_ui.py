from playwright.sync_api import sync_playwright

def verify_frontend():
    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        # Create a context with desktop viewport
        context = browser.new_context(viewport={'width': 1280, 'height': 800})
        page = context.new_page()

        # NOTE: In this environment, we can't easily start the full Spring Boot app
        # because it requires a database connection which might fail without
        # a local Postgres instance running.
        # However, since we are verifying static Thymeleaf templates and CSS,
        # we can verify the HTML structure/CSS visually if we mock it or
        # if we could serve the static files.

        # BUT, the styles depend on /css/interface.css relative path.
        # A simple python http server won't parse thymeleaf, but can serve the raw html.
        # Let's try to verify the CSS file presence and maybe render a raw HTML file
        # that mimics the template with the css linked relative.

        # Better approach:
        # 1. Read interface.css content
        # 2. Read signup.html content
        # 3. Create a temporary HTML file that embeds the CSS (or links to it relatively)
        # 4. Open that file in Playwright

        import os

        with open('src/main/resources/static/css/interface.css', 'r') as f:
            css_content = f.read()

        with open('src/main/resources/templates/signup.html', 'r') as f:
            html_content = f.read()

        # Replace thymeleaf attributes to make it viewable (basic cleanup)
        # and inject CSS directly for easy rendering without server

        html_content = html_content.replace('href="/css/interface.css"', '')
        html_content = html_content.replace('</head>', f'<style>{css_content}</style></head>')

        # Remove thymeleaf attributes that might cause rendering issues in raw browser
        # (though browsers usually ignore them)

        temp_file = os.path.abspath("verification/temp_signup.html")
        with open(temp_file, 'w') as f:
            f.write(html_content)

        page.goto(f"file://{temp_file}")

        page.screenshot(path="verification/signup_desktop.png")

        # Mobile view
        context_mobile = browser.new_context(viewport={'width': 375, 'height': 812})
        page_mobile = context_mobile.new_page()
        page_mobile.goto(f"file://{temp_file}")
        page_mobile.screenshot(path="verification/signup_mobile.png")

        browser.close()

if __name__ == "__main__":
    verify_frontend()
