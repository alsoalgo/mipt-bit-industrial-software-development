#!/usr/bin/python3
# [deprecated] Использовался для автоматической генерации конфигурации структуры кластера
# [deprecated] Решил отказаться, так как встрял на подъеме sshd, конфигурации ssh и т.п.
import os

def print_inventory(mip, hip):
    out = open("./ansible/inventories/servers.yml", "w")
    print("""[all]
manager ansible_host=""" + mip + """ ansible_ssh_user=alsoalgo ansible_ssh_pass=alsoalgo
host ansible_host=""" + hip + """ ansible_ssh_user=alsoalgo ansible_ssh_pass=alsoalgo
[hosts]
host ansible_host="""+ hip + """ ansible_ssh_user=alsoalgo ansible_ssh_pass=alsoalgo
""", file=out)


def main():
    ipsf = open("ips", 'r')
    lines = ipsf.readlines()
    manager_ip = lines[0].split("=")[1].strip()
    host_ip = lines[1].split("=")[1].strip()
    print_inventory(manager_ip, host_ip)


if __name__ == "__main__":
    main()