package com.divyanshu.backend.service;
import com.divyanshu.backend.Exception.NotFoundException;
import com.divyanshu.backend.dto.DocumentDto;
import com.divyanshu.backend.model.Document;
import com.divyanshu.backend.model.SubModule;
import com.divyanshu.backend.repository.DocumentRepository;
import com.divyanshu.backend.repository.SubModuleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class DocumentService {


    private static final String UPLOAD_DIRECTORY = "";
    private static final int MAX_VERSIONS = 10;


    @Autowired
    private SubModuleRepository subModuleRepository;


    @Autowired
    private DocumentRepository documentRepository;


    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private com.divyanshu.backend.service.EncryptionService encryptionService;


    public DocumentDto addDocumentFile(MultipartFile file, int subModuleId) throws Exception {
        String filename = file.getOriginalFilename();
        String version = "1.0";


        Path filePath = Paths.get(UPLOAD_DIRECTORY , filename);
        Files.write(filePath,file.getBytes());


        encryptionService.encryptDoc(filePath.toFile(), Paths.get(UPLOAD_DIRECTORY, "encrypted_" + filename).toFile());


        SubModule subModule = subModuleRepository.findById(subModuleId)
                .orElseThrow(()-> new NotFoundException("Sub Module Not Found"));


        Document document = new Document();
        document.setName(filename);
        document.setPath(filePath.toString());
        document.setVersion(version);
        document.setEncrypted(true);


        System.out.println( "user:" + document);


        DocumentDto documentDto = modelMapper.map(document , DocumentDto.class);
        documentRepository.save(document);
        return  documentDto;
    }
    public Document editDocument(int documentId , MultipartFile file ) throws Exception {
        Optional<Document> existingDocument = documentRepository.findById(documentId);
        if(existingDocument.isPresent()){
            Document document = existingDocument.get();
            String filename = file.getOriginalFilename();


            String newVersion = incrementVersion(document.getVersion());


//            archiveOldVersion(document);


            Path  filePath = Paths.get(UPLOAD_DIRECTORY , filename);
            Files.write(filePath , file.getBytes());


            encryptionService.encryptDoc(filePath.toFile(), Paths.get(UPLOAD_DIRECTORY, "encrypted_" + filename).toFile());


            document.setName(filename);
            document.setPath(filePath.toString());
            document.setVersion(newVersion);
            document.setEncrypted(true);


            return documentRepository.save(document);
        }else {
            return  null;
        }
    }
    public List<Document> getAllDocuments(){
        return  documentRepository.findAll();
    }
    public  Document getDocument(int documentid){
        return  documentRepository.findById(documentid).orElse(null);
    }
    public byte[] downloadDoc(int documentId) throws  Exception {
        Document document = documentRepository.findById(documentId).orElse(null);
        if(document != null && document.isEncrypted()){
            Path encryptedFilePath = Paths.get(document.getPath());
            Path decryptedFilePath = Paths.get(UPLOAD_DIRECTORY , "decrypted_" + document.getName());
            encryptionService.decryptDoc(encryptedFilePath.toFile(),decryptedFilePath.toFile());
            return  Files.readAllBytes(decryptedFilePath);
        }
        return  new byte[0];
    }
    private  String incrementVersion(String version){
        String[] parts = version.split("\\.");
        int minorVersion = Integer.parseInt(parts[1]);
        minorVersion++;
        return  parts[0] + "." + minorVersion;
    }
    @Scheduled(cron = "0 0 2 * * ?")
    public void reEncryptOldDocuments() {
        System.out.println("Working");
        List<File> oldDocs = findDocumentsNeedingReEncryption();
        for (File doc : oldDocs) {
            try {
                File tempDecrypted = new File("decrypted_" + doc.getAbsolutePath() );
                File tempEncrypted = new File( "encrypted_" + doc.getAbsolutePath() );
                encryptionService.decryptDoc(doc, tempDecrypted);
                encryptionService.encryptDoc(tempDecrypted, tempEncrypted);
                if (doc.delete()) {
                    tempEncrypted.renameTo(doc);
                }
                tempDecrypted.delete();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private List<File> findDocumentsNeedingReEncryption() {
        List<Document> docs = documentRepository.findByEncryptedFalse();
        return docs.stream()
                .map(doc -> new File(doc.getPath()))
                .collect(Collectors.toList());
    }


}
