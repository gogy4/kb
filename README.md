прописать docker-compose up -d --build в консоли в директории проекта, чтобы поднять бд.

так же добавил дамп бд с 3 кейсами и скинами, чтобы не создавать самому вручную.

есть админка, чтобы менять балик пользователям, добавлять кейсы/скины, регистрировать нового адм. Реализовано через сваггер.

доступ к админке только через адм аккаунт. Данные адм: admin@gmail.com, Admin123!Admin

также, если собираетесь добавлять новые кейсы/скины, поменяйте в application.properties строку upload.path=B:/uploads на свою папку, где будут загружатсья картинки.

есть пару ошибок в представлении, но суть проекта - бекенд разработка, поэтому почти весь фронт делал через гпт, есть баг с отрисовкой процентов в апгрейде.
