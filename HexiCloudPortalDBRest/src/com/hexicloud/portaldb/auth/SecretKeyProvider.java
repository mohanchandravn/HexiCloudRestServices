package com.hexicloud.portaldb.auth;

import java.io.IOException;

import java.net.URISyntaxException;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.log4j.Logger;

import org.springframework.stereotype.Component;

@Component
public class SecretKeyProvider {
    private static final Logger logger = Logger.getLogger(SecretKeyProvider.class);
    public byte[] getKey() throws URISyntaxException, IOException {
//        logger.info("Getting the secret key");
//        final URI uri = getClass().getResource("/jwt.key").toURI();
//        Map<String, String> env = new HashMap<>(); 
//        env.put("create", "true");
//        FileSystem zipfs = FileSystems.newFileSystem(uri, env);
//        Path myFolderPath = Paths.get(uri);
        
//        return Files.readAllBytes(Paths.get(this.getClass().getResource("/jwt.key").toURI()));

        return Files.readAllBytes(Paths.get("D:\\HexiCloud\\code\\HexiCloudRestServicesJWTSec\\HexiCloudPortalDBRest\\src\\jwt.key"));
    }
}
