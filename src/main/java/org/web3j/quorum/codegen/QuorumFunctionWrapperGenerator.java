package org.web3j.quorum.codegen;

import javax.lang.model.element.Modifier;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

import com.squareup.javapoet.*;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Type;
import org.web3j.protocol.Web3j;
import org.web3j.quorum.Contract;

/**
 * Modified function wrapper generator for working with Quorum.
 */
public class QuorumFunctionWrapperGenerator extends SolidityFunctionWrapperGenerator {

    private static final String FROM_ADDRESS = "fromAddress";
    private static final String PRIVATE_FOR = "privateFor";

    public static void main(String[] args) throws Exception {

        if (args.length != 6) {
            exitError(USAGE);
        }

        String binaryFileLocation = parsePositionalArg(args, 0);
        String absFileLocation = parsePositionalArg(args, 1);
        String destinationDirLocation = parseParameterArgument(args, "-o", "--outputDir");
        String basePackageName = parseParameterArgument(args, "-p", "--package");

        if (binaryFileLocation.equals("")
                || absFileLocation.equals("")
                || destinationDirLocation.equals("")
                || basePackageName.equals("")) {
            exitError(USAGE);
        }

        new QuorumFunctionWrapperGenerator(
                binaryFileLocation,
                absFileLocation,
                destinationDirLocation,
                basePackageName)
                .generate();
    }

    public QuorumFunctionWrapperGenerator(
            String binaryFileLocation, String absFileLocation, String destinationDirLocation,
            String basePackageName) {
        super(binaryFileLocation, absFileLocation, destinationDirLocation, basePackageName);
    }

    @Override
    protected TypeSpec.Builder createClassBuilder(String className, String binary) {
        return TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addJavadoc(CODEGEN_WARNING)
                .superclass(Contract.class)
                .addField(createBinaryDefinition(binary));
    }

    @Override
    protected MethodSpec buildConstructor() {
        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE)
                .addParameter(String.class, CONTRACT_ADDRESS)
                .addParameter(Web3j.class, WEB3J)
                .addParameter(String.class, FROM_ADDRESS)
                .addParameter(BigInteger.class, GAS_PRICE)
                .addParameter(BigInteger.class, GAS_LIMIT)
                .addParameter(ParameterizedTypeName.get(List.class, String.class), PRIVATE_FOR)
                .addStatement("super($N, $N, $N, $N, $N, $N)",
                        CONTRACT_ADDRESS, WEB3J, FROM_ADDRESS, GAS_PRICE, GAS_LIMIT, PRIVATE_FOR)
                .build();
    }

    @Override
    protected MethodSpec buildLoad(String className) {
        return MethodSpec.methodBuilder("load")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(TypeVariableName.get(className, Type.class))
                .addParameter(String.class, CONTRACT_ADDRESS)
                .addParameter(Web3j.class, WEB3J)
                .addParameter(String.class, FROM_ADDRESS)
                .addParameter(BigInteger.class, GAS_PRICE)
                .addParameter(BigInteger.class, GAS_LIMIT)
                .addParameter(ParameterizedTypeName.get(List.class, String.class), PRIVATE_FOR)
                .addStatement("return new $L($L, $L, $L, $L, $L, $L)",
                        className, CONTRACT_ADDRESS, WEB3J, FROM_ADDRESS, GAS_PRICE, GAS_LIMIT, PRIVATE_FOR)
                .build();
    }

    @Override
    protected MethodSpec.Builder getDeployMethodSpec(String className) {
        return MethodSpec.methodBuilder("deploy")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ParameterizedTypeName.get(
                        ClassName.get(Future.class), TypeVariableName.get(className, Type.class)))
                .addParameter(Web3j.class, WEB3J)
                .addParameter(String.class, FROM_ADDRESS)
                .addParameter(BigInteger.class, GAS_PRICE)
                .addParameter(BigInteger.class, GAS_LIMIT)
                .addParameter(BigInteger.class, INITIAL_VALUE)
                .addParameter(ParameterizedTypeName.get(List.class, String.class), PRIVATE_FOR);
    }

    protected MethodSpec buildDeployAsyncWithParams(
            MethodSpec.Builder methodBuilder, String className, String inputParams) {
        methodBuilder.addStatement("$T encodedConstructor = $T.encodeConstructor(" +
                        "$T.<$T>asList($L)" +
                        ")",
                String.class, FunctionEncoder.class, Arrays.class, Type.class, inputParams);
        methodBuilder.addStatement(
                "return deployAsync($L.class, $L, $L, $L, $L, $L, encodedConstructor, $L, $L)",
                className, WEB3J, FROM_ADDRESS, GAS_PRICE, GAS_LIMIT, BINARY, INITIAL_VALUE,
                PRIVATE_FOR);
        return methodBuilder.build();
    }

    protected MethodSpec buildDeployAsyncNoParams(
            MethodSpec.Builder methodBuilder, String className) {
        methodBuilder.addStatement("return deployAsync($L.class, $L, $L, $L, $L, $L, \"\", $L, $L)",
                className, WEB3J, FROM_ADDRESS, GAS_PRICE, GAS_LIMIT, BINARY, INITIAL_VALUE,
                PRIVATE_FOR);
        return methodBuilder.build();
    }

}
