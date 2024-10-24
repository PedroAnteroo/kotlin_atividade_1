import java.util.Scanner

// Classe Produto
data class Produto(
    val codigo: Int,
    var nome: String,
    var valorUnitario: Double,
    var tipoUnidade: String
)

// Classe Item de Compra
data class ItemCompra(
    val produto: Produto,
    val quantidade: Int
)

// Classe Lojinha
class Lojinha {
    private val produtos = mutableListOf<Produto>()
    private val carrinho = mutableListOf<ItemCompra>()
    private val scanner = Scanner(System.`in`)

    // Cadastro de Produto
    fun cadastrarProduto() {
        println("Informe o código do produto:")
        val codigo = scanner.nextInt()
        scanner.nextLine() // Limpar buffer
        println("Informe o nome do produto:")
        val nome = scanner.nextLine()
        println("Informe o valor unitário do produto:")
        val valorUnitario = scanner.nextDouble()
        scanner.nextLine() // Limpar buffer
        println("Informe o tipo de unidade (ex: kg, unidade, litro):")
        val tipoUnidade = scanner.nextLine()

        produtos.add(Produto(codigo, nome, valorUnitario, tipoUnidade))
        println("Produto cadastrado com sucesso!")
    }

    // Alteração de Produto
    fun alterarProduto() {
        println("Informe o código do produto que deseja alterar:")
        val codigo = scanner.nextInt()
        val produto = produtos.find { it.codigo == codigo }

        if (produto != null) {
            println("Informe o novo nome do produto:")
            scanner.nextLine() // Limpar buffer
            val nome = scanner.nextLine()
            println("Informe o novo valor unitário do produto:")
            val valorUnitario = scanner.nextDouble()
            println("Informe o novo tipo de unidade do produto:")
            scanner.nextLine() // Limpar buffer
            val tipoUnidade = scanner.nextLine()

            produto.nome = nome
            produto.valorUnitario = valorUnitario
            produto.tipoUnidade = tipoUnidade

            println("Produto alterado com sucesso!")
        } else {
            println("Produto não encontrado.")
        }
    }

    // Exclusão de Produto
    fun excluirProduto() {
        println("Informe o código do produto que deseja excluir:")
        val codigo = scanner.nextInt()
        val produtoRemovido = produtos.removeIf { it.codigo == codigo }

        if (produtoRemovido) {
            println("Produto removido com sucesso!")
        } else {
            println("Produto não encontrado.")
        }
    }

    // Realizar Venda
    fun realizarVenda() {
        while (true) {
            println("Informe o código do produto (ou 0 para finalizar a compra):")
            val codigo = scanner.nextInt()

            if (codigo == 0) break

            val produto = produtos.find { it.codigo == codigo }

            if (produto != null) {
                println("Informe a quantidade:")
                val quantidade = scanner.nextInt()

                carrinho.add(ItemCompra(produto, quantidade))
                println("${quantidade} x ${produto.nome} adicionado ao carrinho.")
            } else {
                println("Produto não encontrado.")
            }
        }
        finalizarCompra()
    }

    // Finalizar Compra e Selecionar Forma de Pagamento
    private fun finalizarCompra() {
        if (carrinho.isEmpty()) {
            println("Nenhum item no carrinho.")
            return
        }

        var total = 0.0
        println("Itens no carrinho:")
        carrinho.forEach { item ->
            val subtotal = item.quantidade * item.produto.valorUnitario
            println("${item.quantidade} x ${item.produto.nome} - R$ $subtotal")
            total += subtotal
        }
        println("Total da compra: R$ $total")

        println("Escolha a forma de pagamento: 1. Pix, 2. Cartão, 3. Dinheiro")
        when (scanner.nextInt()) {
            1 -> {
                println("Código PIX: 1234567890")
            }
            2 -> {
                println("Escolha: 1. Crédito, 2. Débito")
                val tipoCartao = if (scanner.nextInt() == 1) "Crédito" else "Débito"
                println("Informe o número do cartão:")
                val numeroCartao = scanner.next()
                println("Pagamento $tipoCartao efetuado com sucesso! Cartão: $numeroCartao")
            }
            3 -> {
                println("Informe o valor pago em dinheiro:")
                val valorPago = scanner.nextDouble()
                if (valorPago >= total) {
                    val troco = valorPago - total
                    println("Pagamento efetuado! Troco: R$ $troco")
                } else {
                    println("Valor insuficiente!")
                }
            }
            else -> println("Forma de pagamento inválida.")
        }
        carrinho.clear() // Limpa o carrinho após a venda
    }
}

// Função principal
fun main() {
    val lojinha = Lojinha()
    val scanner = Scanner(System.`in`)

    while (true) {
        println(
            """
            Escolha uma opção:
            1. Cadastrar Produto
            2. Alterar Produto
            3. Excluir Produto
            4. Realizar Venda
            5. Sair
        """.trimIndent()
        )

        when (scanner.nextInt()) {
            1 -> lojinha.cadastrarProduto()
            2 -> lojinha.alterarProduto()
            3 -> lojinha.excluirProduto()
            4 -> lojinha.realizarVenda()
            5 -> {
                println("Saindo...")
                return
            }
            else -> println("Opção inválida.")
        }
    }
}
