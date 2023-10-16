import redis

def main():
    client = redis.StrictRedis(host='redis', port=6379, db=0)

    client.set('k', 'Hello, World!')
    value = client.get('k')
    print(value.decode('utf-8'))
    with open("value.txt", "w") as f:
        f.write(value.decode('utf-8'))

if __name__ == "__main__":
    main()

